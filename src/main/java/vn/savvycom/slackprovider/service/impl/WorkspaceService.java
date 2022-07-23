package vn.savvycom.slackprovider.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.savvycom.slackprovider.domain.entity.Recipient;
import vn.savvycom.slackprovider.domain.entity.Workspace;
import vn.savvycom.slackprovider.repository.WorkspaceRepository;
import vn.savvycom.slackprovider.service.IWorkspaceService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceService implements IWorkspaceService {
    private final RecipientService recipientService;
    private final WorkspaceRepository workspaceRepository;

    @Override
    public Workspace findActiveWorkspaceById(String id) {
        Optional<Workspace> workspace = workspaceRepository.findById(id);
        if (workspace.isEmpty() || !workspace.get().isActive()) {
            throw new IllegalArgumentException("Not found any workspace with id " + id);
        }
        return workspace.get();
    }

    @Override
    public List<Workspace> findAllActiveWorkspace() {
        return workspaceRepository.findAll()
                .stream().filter(Workspace::isActive)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Workspace workspace) {
        workspaceRepository.save(workspace);
    }

    @Override
    public void delete(String id) {
        Workspace activeWorkspace = findActiveWorkspaceById(id);
        List<Recipient> activeRecipients = recipientService.findActiveRecipientByWorkspaceId(id);
        recipientService.deleteRecipients(activeRecipients);
        activeWorkspace.setActive(false);
        save(activeWorkspace);
    }
}
